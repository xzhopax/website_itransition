-- Table: users
#
# -- Table: roles
# CREATE TABLE roles (
#                        id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
#                        name VARCHAR(100) NOT NULL
# )
#     ENGINE = InnoDB;
#
# -- Table for mapping user and roles: user_roles
# CREATE TABLE user_roles (
#                             user_id INT NOT NULL,
#                             role_id INT NOT NULL,
#
#                             FOREIGN KEY (user_id) REFERENCES users (id),
#                             FOREIGN KEY (role_id) REFERENCES roles (id),
#
#                             UNIQUE (user_id, role_id)
# )
#     ENGINE = InnoDB;

-- Insert data

# INSERT INTO users VALUES (1, 'proselyte', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');
#
# INSERT INTO roles VALUES (1, 'ROLE_USER');
# INSERT INTO roles VALUES (2, 'ROLE_ADMIN');
#
# INSERT INTO user_roles VALUES (1, 2);



# CREATE TABLE authorities (
#                              username varchar(15),
#                              authority varchar(25),
#                              FOREIGN KEY (username) references users(username)
# ) ;

# INSERT INTO itransition.users (username, password, enabled)
# VALUES
# ('zaur', '{noop}zaur', 1),
# ('elena', '{noop}elena', 1),
# ('ivan', '{noop}ivan', 1);
#
# INSERT INTO itransition.authorities (username, authority)
# VALUES
# ('zaur', 'ROLE_EMPLOYEE'),
# ('elena', 'ROLE_HR'),
# ('ivan', 'ROLE_HR'),
# ('ivan', 'ROLE_MANAGER');
