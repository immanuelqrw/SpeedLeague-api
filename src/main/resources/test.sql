INSERT INTO
  "Runner" (
    "name",
    "joinedOn",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('Ijano','2019-09-12T02:58:49.385','2019-09-12T02:58:49.384','2019-09-12T02:58:49.385',NULL,'13c7bd44-0cb3-45ba-b6fd-f20a81695b32');

INSERT INTO
  "League" (
    "name",
    "type",
    "season",
    "tierName",
    "tierLevel",
    "startedOn",
    "defaultTime",
    "defaultPoints",
    "runnerLimit",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('Ijano Part 1','POOL',1,'Serie A',1,'2019-09-03T15:23:11',0,1,20,'2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b367-99f9-4d8a-9c76-3659976b4e54');

INSERT INTO
  "LeagueRunner" (
    "leagueId",
    "runnerId",
    "joinedOn",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','13c7bd44-0cb3-45ba-b6fd-f20a81695b32','2019-09-12T03:09:00.431','2019-09-12T03:09:00.431','2019-09-12T03:09:00.431',NULL,'68c068d1-b504-40c1-86de-89eab2c1fc13');

INSERT INTO
  "Race" (
    "name",
    "leagueId",
    "startedOn",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('Snapperoo','6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-03T15:23:11','2019-09-12T03:08:43.007','2019-09-12T03:08:43.007',NULL,'a89f45ea-154e-4856-bb23-81925a4f40d8');

INSERT INTO
  "RaceRunner" (
    "raceId",
    "runnerId",
    "time",
    "outcome",
    "placement",
    "joinedOn",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('a89f45ea-154e-4856-bb23-81925a4f40d8','13c7bd44-0cb3-45ba-b6fd-f20a81695b32',1290,'COMPLETED',NULL,'2019-09-12T03:09:00.431','2019-09-12T03:09:00.431','2019-09-12T03:09:00.431',NULL,'68c068d1-b504-40c1-86de-89eab2c1fc13');


INSERT INTO
  "PlayoffRule" (
    "qualifier",
    "count",
    "leagueId",
    "addedOn",
    "order",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('POINTS',1,'6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-10T04:26:01.379',1,'2019-09-10T04:26:01.502','2019-09-10T04:26:01.502',NULL,'1e29e7b6-7389-495f-9a6d-924061d4f219');

INSERT INTO
  "PointRule" (
    "placement",
    "amount",
    "leagueId",
    "addedOn",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  (1,10,'6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-10T04:26:01.379','2019-09-10T04:26:01.502','2019-09-10T04:26:01.502',NULL,'8f54e7b6-7389-495f-9a6d-924061d4f219'),
  (2,7,'6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-10T04:26:01.379','2019-09-10T04:26:01.502','2019-09-10T04:26:01.502',NULL,'8f55e7b6-7389-495f-9a6d-924061d4f219'),
  (3,5,'6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-10T04:26:01.379','2019-09-10T04:26:01.502','2019-09-10T04:26:01.502',NULL,'8f56e7b6-7389-495f-9a6d-924061d4f219'),
  (4,4,'6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-10T04:26:01.379','2019-09-10T04:26:01.502','2019-09-10T04:26:01.502',NULL,'8f57e7b6-7389-495f-9a6d-924061d4f219'),
  (5,3,'6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-10T04:26:01.379','2019-09-10T04:26:01.502','2019-09-10T04:26:01.502',NULL,'8f58e7b6-7389-495f-9a6d-924061d4f219'),
  (6,2,'6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-10T04:26:01.379','2019-09-10T04:26:01.502','2019-09-10T04:26:01.502',NULL,'8f59e7b6-7389-495f-9a6d-924061d4f219');

--

INSERT INTO
  "Game" (
    "name",
    "shorthand",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('Pokémon Snap','pkmnsnap','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b367-99f9-4d8a-9c76-3659976b4e54');

INSERT INTO
  "System" (
    "name",
    "isEmulated",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('N64', FALSE,'2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b367-99f9-4d8a-9c76-3659976b4e54'),
  ('N64', TRUE,'2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b307-99f9-4d8a-9c76-3659976b4e54'),
  ('Wii VC', FALSE,'2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b317-99f9-4d8a-9c76-3659976b4e54'),
  ('Wii VC', TRUE,'2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b327-99f9-4d8a-9c76-3659976b4e54'),
  ('Wii U VC', FALSE,'2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b337-99f9-4d8a-9c76-3659976b4e54'),
  ('Wii U VC', TRUE,'2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b347-99f9-4d8a-9c76-3659976b4e54');

INSERT INTO
  "Category" (
    "name",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('Any%','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b367-99f9-4d8a-9c76-3659976b4e54'),
  ('100%','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b317-99f9-4d8a-9c76-3659976b4e54'),
  ('Challenge Score','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b327-99f9-4d8a-9c76-3659976b4e54'),
  ('100% Wonderful','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b337-99f9-4d8a-9c76-3659976b4e54'),
  ('2p1c','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b347-99f9-4d8a-9c76-3659976b4e54'),
  ('250k Points','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b357-99f9-4d8a-9c76-3659976b4e54'),
  ('Magikarp%','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b377-99f9-4d8a-9c76-3659976b4e54');

INSERT INTO
  "Cart" (
    "gameId",
    "systemId",
    "region",
    "version",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b367-99f9-4d8a-9c76-3659976b4e54','NTSC_J','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b367-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b367-99f9-4d8a-9c76-3659976b4e54','NTSC_U','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b301-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b307-99f9-4d8a-9c76-3659976b4e54','NTSC_J','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b302-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b307-99f9-4d8a-9c76-3659976b4e54','NTSC_U','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b303-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b317-99f9-4d8a-9c76-3659976b4e54','NTSC_J','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b304-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b317-99f9-4d8a-9c76-3659976b4e54','NTSC_U','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b305-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b327-99f9-4d8a-9c76-3659976b4e54','NTSC_J','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b306-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b327-99f9-4d8a-9c76-3659976b4e54','NTSC_U','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b307-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b337-99f9-4d8a-9c76-3659976b4e54','NTSC_J','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b308-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b337-99f9-4d8a-9c76-3659976b4e54','NTSC_U','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b309-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b347-99f9-4d8a-9c76-3659976b4e54','NTSC_J','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b310-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b347-99f9-4d8a-9c76-3659976b4e54','NTSC_U','ANY','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b311-99f9-4d8a-9c76-3659976b4e54');

INSERT INTO
  "Speedrun" (
    "categoryId",
    "cartId",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b367-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b301-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b301-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b302-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b302-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b303-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b303-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b304-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b304-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b305-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b305-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b306-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b306-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b307-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b307-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b308-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b308-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b309-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b309-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b310-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b310-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b311-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b311-99f9-4d8a-9c76-3659976b4e54');

INSERT INTO
  "LeagueSpeedrun" (
    "leagueId",
    "speedrunId",
    "createdOn",
    "modifiedOn",
    "removedOn",
    "id"
  )
VALUES
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b367-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b367-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b301-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b301-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b302-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b302-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b303-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b303-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b304-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b304-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b305-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b305-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b306-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b306-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b307-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b307-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b308-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b308-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b309-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b309-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b310-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b310-99f9-4d8a-9c76-3659976b4e54'),
  ('6b23b367-99f9-4d8a-9c76-3659976b4e54','6b23b311-99f9-4d8a-9c76-3659976b4e54','2019-09-12T03:08:32.33','2019-09-12T03:08:32.33',NULL,'6b23b311-99f9-4d8a-9c76-3659976b4e54');
