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
	month = '$P{MONTH}'
	AND report.year = $P{YEAR}
	AND (report.hours > 0 OR report.preaching_fifteen_min_less = 1)
	AND publisher.special_pioneer = 0
	AND publisher.missionary = 0

ORDER BY report.id ASC