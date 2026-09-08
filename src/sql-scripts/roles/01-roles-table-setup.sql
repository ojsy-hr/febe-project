CREATE TABLE `febe_project`.`roles` (
    `role_id` INT NOT NULL COMMENT 'The unique identifier of a role.',
    `role` VARCHAR(64) NOT NULL DEFAULT 'USER' COMMENT 'The name of the role that a user can be assigned.',
    PRIMARY KEY (`role_id`),
    UNIQUE INDEX `role_id_UNIQUE` (`role_id` ASC) VISIBLE,
    UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE)
COMMENT = 'Stores each role once with a PK. Users can be assigned to multiple roles.';