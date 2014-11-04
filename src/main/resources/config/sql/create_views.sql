------------------------------------------------------------------------------
--
--	左边网数据库创建视图脚本
--	
--  视图(view)
--
------------------------------------------------------------------------------

-----------------------------创建左边网的视图--BEGIN-------------------------------------- 

--财务视图--
create or replace view financeview as
select f."F_ID",f."GMT_CREATE",f."GMT_MODIFIED",f."OR_ID",f."F_TYPE",f."F_SERIALNUMBER",f."ACCOUNT_C_ID",f."TOUR_C_ID",f."F_RECEIVABLE",f."F_RECEIPT",f."APP_ID"
,l.l_title,l.l_id,l.l_groupnumber,o.or_gogrouptime,o.or_name,o.custom_id as m_id,(o.or_adultcount+o.or_childcount+o.or_babycount) as orderpeoplecount
,o.or_orderid,o.gmt_create as orcreatetime,l.z_id,ct.c_customname,ct.c_name as tourname,ca.c_name as accountname
from travel_finance f 
join travel_order o on f.or_id=o.or_id 
join travel_line l on o.l_id=l.l_id 
join travel_company ct on f.tour_c_id=ct.c_id 
join travel_company ca on f.account_c_id=ca.c_id;
    
-----------------------------创建左边网的视图--END----------------------------------------