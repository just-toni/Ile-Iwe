set foreign_key_checks = 0;

truncate table learning_party;
truncate table authority;
truncate table instructor;

insert into learning_party(id, email, password, enabled)
values(123, 'toni@mail.com', 'toni123', false),
      (124, 'tobi@mail.com', 'tobi123', false),
      (125, 'tonye@mail.com', 'tonye123', false),
      (126, 'tomi@mail.com', 'tomi123', false);

set foreign_key_checks = 1;