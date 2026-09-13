CREATE TABLE `febe_project`.`users_roles` (
    `user_id` INT NOT NULL COMMENT 'The foreign key relation to user table.',
    `role_id` INT NOT NULL COMMENT 'The foreign key relation to roles table.',
    PRIMARY KEY (`user_id`, `role_id`),
    INDEX `role_id_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `user_id`
      FOREIGN KEY (`user_id`)
          REFERENCES `febe_project`.`user` (`user_id`)
          ON DELETE NO ACTION
          ON UPDATE NO ACTION,
    CONSTRAINT `role_id`
      FOREIGN KEY (`role_id`)
          REFERENCES `febe_project`.`roles` (`role_id`)
          ON DELETE NO ACTION
          ON UPDATE NO ACTION)
COMMENT = 'Creates the relationship between a user and roles. A user can have many roles.';