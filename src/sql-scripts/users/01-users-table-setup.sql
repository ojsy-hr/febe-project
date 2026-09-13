CREATE TABLE `febe_project`.`user` (
    `user_id` INT NOT NULL COMMENT 'The unique identifier of a user.',
    `email` VARCHAR(75) NOT NULL COMMENT 'The user emails set at sign up. Used to log in and change the user password from the front end.',
    `password` VARCHAR(64) NOT NULL COMMENT 'The user password set at sign up. Can be changed during password recovery or after logging in on the front end.',
    `hint_phrase` VARCHAR(16) NOT NULL COMMENT 'The user hint phrase set at sign up. Used during password recovery if forgotten and can be changed when logged in to the front end.',
    `created_date` DATETIME NOT NULL COMMENT 'The date the user account was created. Metadata only.',
    `modified_date` DATETIME NOT NULL COMMENT 'The date the user account was modified. Metadata only.',
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
COMMENT = 'Stores user log in data. Can be updated by the user from the front end.';