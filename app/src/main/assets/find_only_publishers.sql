SELECT * FROM publisher
WHERE publisher.publisher = 1
    AND publisher.deaf = 0 AND publisher.changed_congregation = 0
ORDER BY publisher.name ASC
