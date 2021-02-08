SELECT 

	report.id,
	report.id_publisher,
	report.year,
	report.month,
	SUM(report.publications) AS publications,
	SUM(report.videos) AS videos,
	SUM(report.hours) AS hours,
	SUM(report.revisited) AS revisited,
	SUM(report.bible_couses) AS bible_couses,
	SUM(report.credit) AS credit,
	report.auxiliary_pioneer,
	report.auxiliary_pioneer_type,
	report.preaching_fifteen_min_less,
	report.notes
	
FROM report
INNER JOIN publisher ON publisher.id = report.id_publisher

WHERE 
	report.year = $P{YEAR} 
	$P{FILTER}
	
GROUP BY report.month
	
ORDER BY publisher.name ASC