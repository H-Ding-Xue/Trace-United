DROP TABLE ContactTraces;

CREATE TABLE ContactTraces (
	ID int NOT NULL IDENTITY(1,1),
	UserID varchar(255) NOT NULL,
	CheckInDateTime varchar(255) NOT NULL,
	CheckOutDateTime varchar(255),
	BusinessAddress varchar(255) NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
)

