USE master
GO

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'sp_dev1')
    BEGIN
        create database sp_dev1 collate Croatian_100_CI_AI_KS_SC_UTF8;
    END;
GO

USE sp_dev1;
GO
