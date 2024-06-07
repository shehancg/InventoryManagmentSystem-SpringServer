@echo off

REM MySQL Database Information
set DB_USER=root
set DB_PASSWORD=shehanroot97$$
set DB_NAME=defaultdb

REM Backup Directory
set BACKUP_DIR=C:\Users\SHEHAN\Documents\backup

REM Date Format (optional)
set DATE_FORMAT=%date:~10,4%%date:~4,2%%date:~7,2%_%time:~0,2%%time:~3,2%%time:~6,2%

REM Backup File
set BACKUP_FILE=%BACKUP_DIR%\%DB_NAME%_%DATE_FORMAT%.sql

REM MySQL Dump Command
start /min cmd /c "mysqldump -u%DB_USER% -p%DB_PASSWORD% --databases %DB_NAME% > %BACKUP_FILE%"

REM Delete old backups (retain only the most recent 30 days)
forfiles /p %BACKUP_DIR% /s /m *.* /d -30 /c "cmd /c if @isdir==FALSE del @file"