-- 1) все книги
select * 
from books
    where is_deleted='0'
    
-- 2) поиск по книгам
select * 
from books
    where instr(title||' '||author, ?)>0
    
-- 3) most popular
select * 
from books
    where rating=5
    
-- 4) добавление книги
insert into books(title, author,image)
    values (?, ?, ?)

-- 5) все доступные теги
select * 
from AVAILABLE_TAGS

-- 6) добавление нового тега
insert into AVAILABLE_TAGS(tag)
    values (?)
    
-- 7) добавление тега для книги
insert into BOOK_TAGS(book, tag)
    values (?, 
        (select id
        from AVAILABLE_TAGS
            where tag = ?))

-- 8) все теги для книги
select * 
from AVAILABLE_TAGS
    where id in (
        select tag 
        from BOOK_TAGS
            where book=?)

-- 9) все нотификации 
select * 
from NOTIFICATIONS

-- 10) последние n нотификаций
select * 
from NOTIFICATIONS
    where rownum <= ?
    
-- 11) добавление нотификации
insert into NOTIFICATIONS (BOOK, SEARCHTEXT, CATEGORY, TYPE) 
    values (?, 
            ?, 
            ?,
            (select id
             from NOTIFICATION_TYPES
                where type = ?))
                