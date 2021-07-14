SELECT 
	month,
	COUNT(*) AS number_meeeting,
	SUM(amount_deaf) total,
	ROUND(SUM(amount_deaf)*1.0/COUNT(*), 2) AS average
	
FROM assistance

WHERE year = $P{YEAR}
	AND type = '$P{TYPE}'

GROUP BY month