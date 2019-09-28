-- 1) ��� �����
select * 
from books
    order by id
    
-- 2) ����� �� ������
select * 
from books
    where instr(lower(title)||' '||lower(author), ?)>0
    
-- 3) most popular
select * 
from books
    where rating=?
    
-- 4) ���������� �����
insert into books(title, author,imageBytes)
    values (?, ?, ?)

-- 5) ��� ��������� ����
select * 
from AVAILABLE_TAGS

-- 6) ���������� ������ ����
insert into AVAILABLE_TAGS(tag)
    values (?)
    
-- 7) ���������� ���� ��� �����
insert into BOOK_TAGS(book, tag)
    values (?, 
        (select id
        from AVAILABLE_TAGS
            where tag = ?))

-- 8) ��� ���� ��� �����
select * 
from AVAILABLE_TAGS
    where id in (
        select tag 
        from BOOK_TAGS
            where book=?)

-- 9) ��� ����������� 
select NOTIFICATIONS.id, book, search_text, category, NOTIFICATION_TYPES.type, "DATE"
from NOTIFICATIONS
    inner join NOTIFICATION_TYPES on NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE
    Order by "DATE"

-- 10) ��������� n �����������
select *
from (select NOTIFICATIONS.id, book, search_text, category, NOTIFICATION_TYPES.type, "DATE"
    from NOTIFICATIONS
       inner join NOTIFICATION_TYPES on NOTIFICATION_TYPES.ID = NOTIFICATIONS.TYPE
    order by "DATE")
where rownum <= ?
   
        
-- 11) ���������� �����������
insert into NOTIFICATIONS (BOOK, SEARCH_TEXT, CATEGORY, TYPE) 
    values (?, 
            ?, 
            ?,
            (select id
             from NOTIFICATION_TYPES
                where type = ?))
            
-- 12) ��������� �������� �����
update books
    set rating=?
    where id=?

                
                