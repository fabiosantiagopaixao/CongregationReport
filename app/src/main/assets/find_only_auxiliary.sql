SELECT
    publisher.id,
    publisher.name,
    publisher.last_name,
    publisher.gender,
    publisher.birth,
    publisher.baptism,
    publisher.cell_phone,
    publisher.phones,
    publisher.email,
    publisher.city,
    publisher.neighborhood,
    publisher.address,
    publisher.number,
    publisher.gps,
    publisher.family_head,
    publisher.group_congregation,
    publisher.publisher boolean,
    publisher.regular_pioneer boolean,
    publisher.special_pioneer boolean,
    publisher.missionary boolean ,
    publisher.deaf boolean,
    publisher.elder boolean,
    publisher.sup_group boolean,
    publisher.servant_ministerial boolean,
    publisher.group_help boolean,
    publisher.anointed boolean,
    publisher.coordinator boolean,
    publisher.secretary boolean,
    publisher.sup_service boolean,
    publisher.user_name,
    publisher.password,
    publisher.notes text

FROM publisher
INNER JOIN report ON publisher.id = report.id_publisher
WHERE report.auxiliary_pioneer = 1

ORDER BY publisher.name ASC
