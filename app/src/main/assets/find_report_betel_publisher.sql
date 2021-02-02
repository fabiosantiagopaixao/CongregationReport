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
	AND publisher.publisher = 1
	AND report.hours > 0
	AND report.auxiliary_pioneer = 0
	OR report.preaching_fifteen_min_less = 1

ORDER BY publisher.id ASC