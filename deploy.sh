#!/bin/bash

sudo rm /var/lib/tomcat7/logs/catalina.out
mvn clean package -e
sudo rm -rf /var/lib/tomcat7/webapps/*Movie*
sudo cp target/*Movie*.war /var/lib/tomcat7/webapps
sudo service tomcat7 restart
tail -f /var/lib/tomcat7/logs/catalina.out 
