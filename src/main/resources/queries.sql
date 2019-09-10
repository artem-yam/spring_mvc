-- 1) ��� �����
select * 
from books
    where is_deleted='0'
    
-- 2) ����� �� ������
select * 
from books
    where instr(title||' '||author, ?)>0
    
-- 3) most popular
select * 
from books
    where rating=5
    
-- 4) ���������� �����
insert into books(title, author,image)
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
select * 
from NOTIFICATIONS

-- 10) ��������� n �����������
select * 
from NOTIFICATIONS
    where rownum <= ?
    
-- 11) ���������� �����������
insert into NOTIFICATIONS (BOOK, SEARCHTEXT, CATEGORY, TYPE) 
    values (?, 
            ?, 
            ?,
            (select id
             from NOTIFICATION_TYPES
                where type = ?))
                