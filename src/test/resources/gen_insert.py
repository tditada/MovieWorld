import urllib.request
import json
import re
import datetime
import os.path
import sys

#tt0110912 - Pulp Fiction
#tt1028528 - Death Proof
#tt0499549 - Avatar
#tt1045658 - Silver Linings Playbook
QUERY_FNAME = 'insert.sql'
HITS_FNAME = 'hits'

MONTH_NAMES = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec' ]
MONTH_ORDS = {}
for i in range(len(MONTH_NAMES)):
	MONTH_ORDS[MONTH_NAMES[i]] = i+1

FIELDS_TO_CHECK = ['Type', 'Title', 'Runtime', 'Released', 'Plot', 'Genre', 'Director']

SQL_QUERY_HEAD = """INSERT INTO movies(title, creationDate, releaseDate, genres, directorName, runtimeMins, summary, averageScore)
VALUES"""
SQL_QUERY_BODY = """('{title}', TIMESTAMP '{creation_date}', TIMESTAMP '{release_date}',
 '{genres}', '{director}', {runtime}, '{summary}', {avg_score})"""

def postgres_escape(str):
	return re.sub("[\'\"]", "\'\'", str)

def postgres_arr_format(values):
	accum = "\"{val}\"".format(val=values[0])
	
	for val in values[1:]:
		accum = "{accum},\"{val}\"".format(accum=accum, val=val)
	
	return "{" + accum + "}"

def as_imdb_id(num_id):
	str_id = "{0:0>7}".format(num_id)
	return "tt{str_id}".format(str_id=str_id)

def omdb_url(num_id):
	imdb_id = as_imdb_id(num_id)
	return "http://www.omdbapi.com/?i={imdb_id}&t=".format(imdb_id=imdb_id)

def parse_director(json_data):
	directors = re.compile(", +").split(json_data['Director'])
	#My deepest sorry to all the rest of you directors, this is just test data after all
	return postgres_escape(directors[0])

def parse_release_date(json_data):
	global MONTH_ORDS
	
	matches = re.match("(?P<day>\d+)\s*(?P<month>\w+)\s*(?P<year>\d+)", json_data['Released'])
	day = int(matches.group('day'))
	month = MONTH_ORDS[matches.group('month')]
	year = int(matches.group('year'))
	
	return datetime.date(year, month, day)

def parse_runtime(json_data):
	return re.compile("\d+").match(json_data['Runtime']).group(0)

def to_genre_format(str):
	return postgres_escape(str.upper())

def parse_genres(json_data):
	unescaped_genres = re.compile(", +").split(json_data['Genre'])
	escaped_genres = list(map(to_genre_format, unescaped_genres))
	return escaped_genres

def parse_movie(raw_data):
	global FIELDS_TO_CHECK, SQL_QUERY_BODY

	data = raw_data.decode("utf-8")
	json_resp = json.loads(data)
	if json_resp['Response'] == 'False' or\
	 "..." in json_resp['Plot'] :
	 return None

	for field in FIELDS_TO_CHECK:
		val = json_resp[field]
		if val == 'N/A' or val == '' or val == ' ':
			return None

	runtime = parse_runtime(json_resp)

	if int(runtime) < 100: #to rule out mini series, which are listed as movies
		return None

	director = parse_director(json_resp)
	title = postgres_escape(json_resp['Title'])
	summary = postgres_escape(json_resp['Plot'])

	genres = parse_genres(json_resp)

	if 'ADULT' in genres:
		return None

	release_date = parse_release_date(json_resp)
	creation_date = datetime.date(2014, 9, 10)

	query_str = SQL_QUERY_BODY.format(
		title = title,
	 	creation_date = creation_date,
		release_date = release_date,
		genres = postgres_arr_format(genres),
		director = director,
		runtime = runtime,
		summary = summary,
		avg_score = 0)
	return query_str

def gen_inserts_for(hits_f, out_f, num_ids):
	global SQL_QUERY_HEAD

	out_f.write(SQL_QUERY_HEAD+"\n")
	max_id = max(num_ids)
	hit_count = 0

	for num_id in num_ids:
		print("Trying {id}...".format(id=num_id))
		url = omdb_url(num_id)
		resp = urllib.request.urlopen(url=url)
		query_str = parse_movie(resp.read())
		
		if query_str != None:
			hit_count = hit_count+1
			print("\t'twas a hit me captain! (#{hit_count})".format(hit_count=hit_count))
			hits_f.write("{id},".format(id=num_id))
			if num_id < max_id:
				out_f.write(query_str+",\n")
			else:
				out_f.write(query_str+";\n")
		else:
			print("\tMiss")


def main():
	global QUERY_FNAME, HITS_FNAME

	params = sys.argv[1:]
	start_id = int(params[0])
	tries = int(params[1])

	#Not the nicest method around, I give you that
	if os.path.isfile(HITS_FNAME):
		with open(HITS_FNAME, "r") as hits_f:
			hits_data = hits_f.read()
			hits_data_len = len(hits_data)
	else:
		hits_data_len = 0

	with open(HITS_FNAME, "w") as hits_f:
		with open(QUERY_FNAME, "w") as out_f:
			if hits_data_len > 0:
				str_hits = re.compile("\d+").findall(hits_data)
				hits = list(map(int, str_hits))
				n_hit = len(hits)
				if n_hit < tries:
					last_hit = int(hits[-1])
					needing = tries - n_hit

					if start_id > last_hit+1:
						first_extra_id = start_id
					else:
						first_extra_id = last_hit+1
					
					num_ids = hits + list(range(first_extra_id, first_extra_id+tries))
				else:
					num_ids = hits
			else:
				num_ids = range(start_id, start_id + tries)

			gen_inserts_for(hits_f, out_f, num_ids)


if __name__ == '__main__':
	main()
