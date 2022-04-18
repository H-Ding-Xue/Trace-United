CREATE TABLE Alerts (
	ID int NOT NULL IDENTITY(1,1),
	UserID varchar(255) NOT NULL,
	AlertMessage varchar(2000) NOT NULL,
	AlertDateTime varchar(255) NOT NULL,
	IsAcknowledged BIT NOT NULL,
	AcknowledgedDateTime varchar(255),
	PRIMARY KEY (ID),
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
)