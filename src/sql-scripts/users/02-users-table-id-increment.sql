CREATE TABLE `users` (
                         `user_id` int NOT NULL AUTO_INCREMENT COMMENT 'The unique identifier of a user.',
                         `email` varchar(75) NOT NULL COMMENT 'The users emails set at sign up. Used to log in and change the users password from the front end.',
                         `password` varchar(68) NOT NULL COMMENT 'The users password set at sign up. Can be changed during password recovery or after logging in on the front end.',
                         `hint_phrase` varchar(16) NOT NULL COMMENT 'The users hint phrase set at sign up. Used during password recovery if forgotten and can be changed when logged in to the front end.',
                         `created_date` datetime DEFAULT NULL COMMENT 'The date the users account was created. Metadata only.',
                         `modified_date` datetime DEFAULT NULL COMMENT 'The date the users account was modified. Metadata only.',
                         `enabled` int NOT NULL DEFAULT '1',
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `user_id_UNIQUE` (`user_id`),
                         UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Stores user log in data. Can be updated by the user from the front end.';