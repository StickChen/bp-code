SELECT
  `Moment`.*,
  `User`.*,
  `Comment`.*
FROM `api_json_demo`.`Moment` AS `Moment`
  INNER JOIN `api_json_demo`.`apijson_user` AS `User` ON `User`.`id` = `Moment`.`userId`
  LEFT JOIN (SELECT *
             FROM `api_json_demo`.`Comment`) AS `Comment` ON `Comment`.`momentId` = `Moment`.`id`
WHERE ((((`User`.`name` REGEXP BINARY 't'))))
LIMIT 100 OFFSET 0