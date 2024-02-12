

INSERT INTO wellness-events.user (user_id, company_name, password, role, username) VALUES ('1', 'vendor_company1', '1234v1', 'vendor', 'vendor1');
INSERT INTO wellness-events.user (user_id, company_name, password, role, username) VALUES ('2', 'vendor_company2', '1234v2', 'vendor', 'vendor2');
INSERT INTO wellness-events.user (user_id, company_name, password, role, username) VALUES ('3', 'hr_company1', '1234hr1', 'hr', 'hr1');
INSERT INTO wellness-events.user (user_id, company_name, password, role, username) VALUES ('4', 'hr_company2', '1234hr2', 'hr', 'hr2');
INSERT INTO wellness-events.user (user_id, company_name, password, role, username) VALUES ('5', 'vendor_admin', '1234vadmin', 'vendorAdmin', 'vadmin');
INSERT INTO wellness-events.user (user_id, company_name, password, role, username) VALUES ('6', 'hr_admin', '1234hradmin', 'hrAdmin', 'hradmin');

INSERT INTO wellness-events.event_type (event_type_id, event_name, user_id) VALUES ('1', 'event1', '1');
INSERT INTO wellness-events.event_type (event_type_id, event_name, user_id) VALUES ('2', 'event2', '2');

INSERT INTO wellness-events.event (event_id, confirmed_date, created_date, location, status, event_type_id, user_id) VALUES ('1', '2023-12-20', '2023-12-01', 'marina bay hall A', 'Approved', '1', '3');
INSERT INTO wellness-events.event (event_id, confirmed_date, created_date, location, status, event_type_id, user_id) VALUES ('2', '2023-12-20,2023-12-21,2023-12-22', '2023-12-02', 'expo hall A', 'Pending', '2', '4');
INSERT INTO wellness-events.event (event_id, confirmed_date, created_date, location, remarks, status, event_type_id, user_id) VALUES ('3', '2023-12-20,2023-12-21,2023-12-22', '2023-12-03', 'expo hall B', 'no budget allocated', 'Rejected', '1', '4');
INSERT INTO wellness-events.event (event_id, confirmed_date, created_date, location, status, event_type_id, user_id) VALUES ('4', '2023-12-23', '2023-12-05', 'expo hall B', 'Approved', '2', '4');
INSERT INTO wellness-events.event (event_id, confirmed_date, created_date, location, status, event_type_id, user_id) VALUES ('5', '2023-12-19,2023-12-22,2023-12-23', '2023-12-06', 'marina bay hall B', 'Pending', '2', '3');
INSERT INTO wellness-events.event (event_id, confirmed_date, created_date, location, remarks, status, event_type_id, user_id) VALUES ('6', '2023-12-18,2023-12-25,2023-12-27', '2023-12-02', 'expo hall A', 'no budget allocated', 'Rejected', '1', '3');


INSERT INTO `wellness-events`.`event_type` (`event_type_id`, `event_name`, `user_id`) VALUES ('2', 'onsite screenings', '14');
UPDATE `wellness-events`.`event_type` SET `user_id` = '13' WHERE (`event_type_id` = '1');


-- create indexes also
-- *future tasks
--    add separate company table with id and names
--    add separate date table to put multiple choices
--    change status to enum

-- vales
-- hrauser1 and 2 in ABC HR and hrauser3 in XYZ HR password 1234hruser3
-- vuser1  in PQR VENDOR and vuser2 in XYZ VENDOR password 1234vuser2
--
