use mis
set character set utf8;
DELIMITER $$
CREATE DEFINER = 'root'@'localhost' PROCEDURE `get_seq`(
        IN v_table_name VARCHAR(50),
        OUT f_new_id INTEGER
    )
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT ''
BEGIN
   declare v_current_id int(11);
   declare v_step int(11);
   set v_step=1;
   set v_current_id=1;
   set v_table_name = lower(v_table_name);
   if NOT EXISTS(select 1 from ts_sequence where lower(f_table_name)=v_table_name) then
        insert into `ts_sequence`(f_table_name,f_current_id,f_step) values(v_table_name,v_current_id,v_step);     
   		set f_new_id=v_current_id;
   else 
         select f_current_id,f_step into v_current_id,v_step from ts_sequence where lower(f_table_name)=v_table_name; 
         set f_new_id = v_current_id+v_step;
         update `ts_sequence` set f_current_id=f_new_id where lower(f_table_name)=v_table_name;  
   end if;
END $$
DELIMITER $$

DELIMITER $$
CREATE DEFINER = 'root'@'localhost' PROCEDURE `pr_pager`(
        IN    p_table_name        VARCHAR(1024),
        IN    p_fields            VARCHAR(1024),
        IN    p_page_size            INT,
        IN    p_page_now            INT,
        IN    p_order_string        VARCHAR(128),
        IN    p_where_string        VARCHAR(1024),
        OUT    p_out_rows            INT
    )
    NOT DETERMINISTIC
    CONTAINS SQL
    SQL SECURITY DEFINER
    COMMENT 'Pagination'
BEGIN

    DECLARE m_begin_row INT DEFAULT 0;
    DECLARE m_limit_string CHAR(64);
   
    SET m_begin_row = (p_page_now - 1) * p_page_size;
    SET m_limit_string = CONCAT(' LIMIT ', m_begin_row, ', ', p_page_size);
    
    SET @COUNT_STRING = CONCAT('SELECT COUNT(*) INTO @ROWS_TOTAL FROM ', p_table_name, ' ', p_where_string);
    SET @MAIN_STRING = CONCAT('SELECT ', p_fields, ' FROM ', p_table_name, ' ', p_where_string, ' ', p_order_string, m_limit_string);

    PREPARE count_stmt FROM @COUNT_STRING;
    EXECUTE count_stmt;
    DEALLOCATE PREPARE count_stmt;
    SET p_out_rows = @ROWS_TOTAL;
    PREPARE main_stmt FROM @MAIN_STRING;
    EXECUTE main_stmt;
    DEALLOCATE PREPARE main_stmt;
    
END $$