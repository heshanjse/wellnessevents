--insert users
INSERT INTO `wellnessevents`.`user` VALUES (1,'vadmin','$2a$10$kQbiugUYliL7yqZTyrH46.BAMcdzLRB24.roSkwO08./v7WGhCu7S','VENDOR_ADMIN','vadmin'),(2,'hradmin','$2a$10$yB6xGGaqj1KcVnQwCvLznufWPkPOusexw0Huv6tZ9bztQUA6aH2oC','HR_ADMIN','hradmin'),(3,'XYZ','$2a$10$D3lwuQxqhufd5IC5Iomd2eS3wOMBpS7nPiDjopdEQJvzFov4vFVym','HR_USER','hrauser1'),(4,'pqr','$2a$10$n11BU.Wswe/C0QmESeOyPuGCFQv2lesgi.jylnwoD/I4mBgtVMJ.e','VENDOR_USER','vuser2'),(5,'cde','$2a$10$PEuX.MTzlvj6JCn41Tzkhej3FW70/VrWx1fUVpOAutC/VdiGZgaBS','VENDOR_USER','vuser1'),(6,'JHX','$2a$10$Km1MBLW2hxOELgz6ezCWGeZSbfNIelOz7A4Zg6zXau4Vg2zS8wM6G','HR_USER','hrauser2');

--insert event types
INSERT INTO `wellnessevents`.`event_type` (`event_name`, `user_id`) VALUES ('health talks', '4');
INSERT INTO `wellnessevents`.`event_type` (`event_name`, `user_id`) VALUES ('onsite screenings', '5');

--insert into events
INSERT INTO wellnessevents.event (event_id, confirmed_date, created_date, location, status, event_type_id, user_id) VALUES ('1', '2023-12-20', '2023-12-01', 'marina bay hall A', 'Approved', '1', '3');
INSERT INTO wellnessevents.event (event_id, confirmed_date, created_date, location, status, event_type_id, user_id) VALUES ('2', '2023-12-20,2023-12-21,2023-12-22', '2023-12-02', 'expo hall A', 'Pending', '2', '6');
INSERT INTO wellnessevents.event (event_id, confirmed_date, created_date, location, remarks, status, event_type_id, user_id) VALUES ('3', '2023-12-20,2023-12-21,2023-12-22', '2023-12-03', 'expo hall B', 'no budget allocated', 'Rejected', '1', '6');
INSERT INTO wellnessevents.event (event_id, confirmed_date, created_date, location, status, event_type_id, user_id) VALUES ('4', '2023-12-23', '2023-12-05', 'expo hall B', 'Approved', '2', '6');
INSERT INTO wellnessevents.event (event_id, confirmed_date, created_date, location, status, event_type_id, user_id) VALUES ('5', '2023-12-19,2023-12-22,2023-12-23', '2023-12-06', 'marina bay hall B', 'Pending', '2', '3');
INSERT INTO wellnessevents.event (event_id, confirmed_date, created_date, location, remarks, status, event_type_id, user_id) VALUES ('6', '2023-12-18,2023-12-25,2023-12-27', '2023-12-02', 'expo hall A', 'no budget allocated', 'Rejected', '1', '3');

--insert tokens
INSERT INTO `token` VALUES (1,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YWRtaW4iLCJpYXQiOjE3MDI0NDkyNjMsImV4cCI6MTcwMjUzNTY2M30.S6bfcj3mxunCXp_DwJBU_HBppFl_8E5FovIB0YgGS5Y','BEARER',1),(2,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocmFkbWluIiwiaWF0IjoxNzAyNDQ5MjczLCJleHAiOjE3MDI1MzU2NzN9.O29RRA3HxTbEixnJaeu79KJMYivYg3rDodpARVW9kIo','BEARER',2),(3,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocmF1c2VyMSIsImlhdCI6MTcwMjQ0OTMyNCwiZXhwIjoxNzAyNTM1NzI0fQ.x0l6kTpsoc-sj4Yt587s1dxeerx1KhfCWMEvKEvcock','BEARER',3),(4,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2dXNlcjIiLCJpYXQiOjE3MDI0NDkzNjQsImV4cCI6MTcwMjUzNTc2NH0.G9GYjM7e2TPtwz-PnMSFeLNqU2YTX6yqlZF654wphDo','BEARER',4),(5,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2dXNlcjEiLCJpYXQiOjE3MDI0NDk0MTMsImV4cCI6MTcwMjUzNTgxM30.H_g1b5tJn_e7e35L7hAJXMEL71LaKJgJDwJgFe3VLVk','BEARER',5),(6,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocmF1c2VyMiIsImlhdCI6MTcwMjQ0OTQ0NSwiZXhwIjoxNzAyNTM1ODQ1fQ.NNJRGooD5jTgky-ZKu4SYHmBRihGZEr9aQESpuacXR8','BEARER',6),(52,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJocmF1c2VyMSIsImlhdCI6MTcwMjQ2MzI5NywiZXhwIjoxNzAyNTQ5Njk3fQ.iT0ia5M9n67cHB1crynKPbR4OLjmvg6OCnHMs_-q1FU','BEARER',3),(53,_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2dXNlcjEiLCJpYXQiOjE3MDI0NjMzNzIsImV4cCI6MTcwMjU0OTc3Mn0.B7hQcofih_X3ZvyBarLsmMHMl5mWk0E1R2daF-sB-Uo','BEARER',5),(54,_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2dXNlcjEiLCJpYXQiOjE3MDI0NjMzOTksImV4cCI6MTcwMjU0OTc5OX0.puYc1nLNbc06EWGUvbZPObxvVnyhn4H8Bs4t8c3iWIg','BEARER',5);

--insert token seq
INSERT INTO `token_seq` VALUES (151);
