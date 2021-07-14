SELECT
	*
FROM publisher

WHERE
    publisher.group_congregation = '$P{GROUP}'
    AND (
        SELECT COUNT(*)
        FROM report
        WHERE report.month = '$P{MONTH}'
            AND report.id_publisher = publisher.id
            AND report.year = $P{YEAR}
    ) = 0 AND publisher.changed_congregation = 0

ORDER BY publisher.name ASC