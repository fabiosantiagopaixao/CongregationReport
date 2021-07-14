SELECT
	
	(AVG(amount_total)) AS total

FROM assistance 

WHERE 
	month = '$P{MONTH}'
	AND year = $P{YEAR}
	AND type = 'WEEKEND'
