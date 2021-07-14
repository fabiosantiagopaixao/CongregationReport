SELECT * FROM publisher
WHERE publisher.publisher = 1
    AND publisher.deaf = 1 AND publisher.changed_congregation = 0
ORDER BY publisher.name ASC
