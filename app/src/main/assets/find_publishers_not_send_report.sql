SELECT 
	*
FROM publisher

WHERE (
        SELECT COUNT(*)
        FROM report
        WHERE report.month = '$P{MONTH}'
            AND report.id_publisher = publisher.id
            AND report.year = $P{YEAR}
    ) = 0

ORDER BY publisher.name ASC