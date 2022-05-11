use userdb;

insert into users(username, email, password) values
    ('fred', 'fred@gmail.com', sha1('fred')),
    ('wilma', 'wilma@gmail.com',sha1('wilma')),
    ('barney', 'barney@gmail.com', sha1('barney')),
    ('betty', 'betty@gmail.com' ,sha1('betty'));
