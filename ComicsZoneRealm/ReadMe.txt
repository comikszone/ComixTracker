/*Creating custom realm on server*/

1) Put file /target/ComicsZoneRealm-1.jar to directory /SERVER_DIRECTORY/glassfish/domains/*your_domain*/lib/

2) Add next strings in file /SERVER_DIRECTORY/glassfish/domains/*your_domain*/config/login.conf:

ComicsZoneRealmModule{
	tk.comicszonetracker.comicszonerealm.realm.ComicsZoneRealmModule required;
};

3) Run command SERVER_DIRECTORY/glassfish/bin/asadmin multimode -f <path_to_file_script.txt>
