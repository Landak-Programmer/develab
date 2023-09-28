CREATE TABLE `parking_info` (
    `car_park_no` varchar(255) NOT NULL,
    `address` varchar(255) NOT NULL,
    `latitude` decimal(8,5)	 NOT NULL,
    `longitude` decimal(8,5)	 NOT NULL,
    `date_created` datetime NOT NULL DEFAULT current_timestamp(),
    `last_updated` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`car_park_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `parking_availability` (
    `car_park_no` varchar(255) NOT NULL,
    `total_lots` int(7) NOT NULL,
    `lots_available` int(7) NOT NULL,
    `date_created` datetime NOT NULL DEFAULT current_timestamp(),
    `last_updated` datetime NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (`car_park_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;