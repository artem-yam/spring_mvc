-- 1) все книги
select * 
from books
    order by id
    
-- 2) поиск по книгам
select * 
from books
    where instr(lower(title)||' '||lower(author), ?)>0
    
-- 3) most popular
select * 
from books
    where rating=?
    
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
select NOTIFICATIONS.id, book, search_text, category, NOTIFICATION_TYPES.type, "DATE"
from NOTIFICATIONS
    inner join NOTIFICATION_TYPES on NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE
    Order by "DATE"

-- 10) последние n нотификаций
select *
from (select NOTIFICATIONS.id, book, search_text, category, NOTIFICATION_TYPES.type, "DATE"
    from NOTIFICATIONS
       inner join NOTIFICATION_TYPES on NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE
    order by "DATE")
where rownum <= ?
   
        
-- 11) добавление нотификации
insert into NOTIFICATIONS (BOOK, SEARCH_TEXT, CATEGORY, TYPE) 
    values (?, 
            ?, 
            ?,
            (select id
             from NOTIFICATION_TYPES
                where type = ?))
            
-- 12) Изменение рейтинга книги
update books
    set rating=?
    where id=?

                
                