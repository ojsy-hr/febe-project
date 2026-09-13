CREATE TABLE `roles` (
                         `role_id` int NOT NULL AUTO_INCREMENT COMMENT 'The unique identifier of a role.',
                         `role` varchar(64) NOT NULL DEFAULT 'USER' COMMENT 'The name of the role that a user can be assigned.',
                         PRIMARY KEY (`role_id`),
                         UNIQUE KEY `role_id_UNIQUE` (`role_id`),
                         UNIQUE KEY `role_UNIQUE` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Stores each role once with a PK. Users can be assigned to multiple roles.';