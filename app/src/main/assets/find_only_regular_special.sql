SELECT * FROM publisher
WHERE publisher.special_pioneer = 1 AND publisher.changed_congregation = 0
ORDER BY publisher.name ASC
