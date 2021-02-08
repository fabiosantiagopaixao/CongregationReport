SELECT 
	
	report.id,
	report.id_publisher,
	report.year,
	report.month,
	report.publications,
	report.videos,
	report.hours,
	report.revisited,
	report.bible_couses,
	report.credit,
	report.auxiliary_pioneer,
	report.auxiliary_pioneer_type,
	report.preaching_fifteen_min_less,
	report.notes
	
FROM report
INNER JOIN publisher ON publisher.id = report.id_publisher

WHERE 
	report.month = '$P{MONTH}'
	AND report.year = $P{YEAR}
	AND publisher.regular_pioneer = 1
	AND report.hours > 0 
	
ORDER BY publisher.name ASC