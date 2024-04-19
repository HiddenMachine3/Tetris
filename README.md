Setup : \


Have mysql setup on your computer with no errors,
start a cmd terminal with admin priveleges(windows key+r, type cmd, ctrl+shift+enter)
,start the mysql server with `net start mysql80`(on windows)

run the following command to run the sql script(which creates the database and its table)
`mysql -u <user_name> -p < "path/to/db_creation_script.sql"`



To run, all you have to do is have jdk (21 preferably) installed and put on your path
`java.exe -classpath "out\production\Tetris;mysql-connector-j-8.3.0.jar" Main.Main`