CREATE TABLE Users (
    UserID varchar(255) NOT NULL,
    Password varchar(255) NOT NULL,
    UserType varchar(255) NOT NULL,
	NRIC varchar(20) NOT NULL,
    [Name] varchar(255),
    PhoneNumber varchar(255),
    EmailAddress varchar(255),
    IsActive BIT NOT NULL,
	PRIMARY KEY (UserID)
);

CREATE TABLE PublicUsers (
	UserID varchar(255) NOT NULL,
    IsVaccinated BIT NOT NULL,
	PRIMARY KEY (UserID),
	FOREIGN KEY (UserID) REFERENCES Users (UserID)
);

CREATE TABLE Businesses (
    UserID varchar(255) NOT NULL,
	BusinessName varchar(255) NOT NULL,
	Businessaddress varchar(255) NOT NULL,
	PRIMARY KEY (UserID),
	FOREIGN KEY (UserID) REFERENCES Users(UserID)
);