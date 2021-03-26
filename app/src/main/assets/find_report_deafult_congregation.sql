SELECT 
	(SELECT COUNT(*) FROM publisher WHERE deaf = 1) AS deaf_school,
	(SELECT COUNT(*) FROM publisher WHERE deaf = 1 AND LENGTH(baptism) > 0) AS deaf_baptism,
	(SELECT COUNT(*) FROM publisher WHERE deaf = 1 AND LENGTH(baptism) == 0) AS deaf_no_baptism,
	(SELECT COUNT(*) FROM publisher WHERE deaf = 1 AND regular_pioneer = 1) AS deaf_regular_pioneer,
	(SELECT COUNT(*) FROM publisher WHERE deaf = 0) AS publishers,
	(SELECT COUNT(*) FROM publisher WHERE deaf = 0 AND LENGTH(baptism) > 0) AS publishers_baptism,
	(SELECT COUNT(*) FROM publisher WHERE deaf = 0 AND LENGTH(baptism) == 0) AS publishers_no_baptism,
	(SELECT COUNT(*) FROM publisher WHERE regular_pioneer) AS total_regular_pioneer