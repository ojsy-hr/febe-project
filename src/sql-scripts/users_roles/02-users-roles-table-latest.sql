CREATE TABLE `users_roles` (
                               `user_id` int NOT NULL COMMENT 'The foreign key relation to users table.',
                               `role_id` int NOT NULL COMMENT 'The foreign key relation to roles table.',
                               PRIMARY KEY (`user_id`,`role_id`),
                               KEY `role_id_idx` (`role_id`),
                               CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
                               CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Creates the relationship between a user and roles. A user can have many roles.';