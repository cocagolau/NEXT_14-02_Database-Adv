import mysql.connector
from contextlib import closing

def connect_db() :
    config = {  'host':'localhost',
                'user':'root',
                'password':'',
                'database':'shortener',
                'charset' :'utf8'}

    con = mysql.connector.connect(**config)
    return con

def do_insert(con, query) :
    with closing(con.cursor()) as cur :
        try :
            cur.execute(query)
            con.commit()
            return True
        except mysql.connector.Error as err :
            print ("Insert Error -  {}".format(err))
            return False

def parse_aof() :
	aof_location = "/Users/Dec7/Program/redis/appendonly.aof"
	f = open(aof_location, "r")

	command_count = 0
	command = []
	all_command = []

	# PARSING AOF
	for line in f :

		if (line[0] == '*') :
			command_count = int(line[1:])
			command = []
			continue
		elif (line[0] == '$') :
			continue
		else :
			command.append(line[:-2])
			command_count -= 1
			if (command_count == 0) :
				all_command.append(command)

	return all_command

def insert_commands(all_command) :
	con = connect_db()
	i = 0;
	# INSERT
	for command in all_command :
		# INSERT OPTION (ex only url: namespace)
		if command[0].upper() == 'set'.upper() :
			sql = "INSERT into redis(kee, value) values (\'%s\', \'%s\')" % (command[1], command[2])
			do_insert(con, sql)
			i += 1
		if i  % 1000 == 0 :
			print i
	con.close()
		
def main() :
	insert_commands(parse_aof())

main()