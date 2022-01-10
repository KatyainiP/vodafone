CREATE TABLE device_details
(
    DateTime    VARCHAR(255),
    EventId     INTEGER,
    ProductId   VARCHAR(255),
    Latitude	VARCHAR(255),
    Longitude	VARCHAR(255),
    Battery	    VARCHAR(255),
    Light	    VARCHAR(255),
    AirplaneMode VARCHAR(255),
    PRIMARY KEY (EventId)
);