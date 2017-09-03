SET timezone TO 'Europe/Helsinki';

CREATE TABLE team (
 "name" text NOT NULL PRIMARY KEY,
 "kg" numeric NOT NULL
);

CREATE TABLE entry (
 "id" serial NOT NULL PRIMARY KEY,
 "team" text NOT NULL REFERENCES team("name"),
 "name" text NOT NULL,
 "kg" numeric NOT NULL,
 "created" timestamp DEFAULT now()
);

CREATE VIEW teamview AS
    SELECT  team."name",
            team."kg" as overallKg,
            SUM(entry."kg") as recentlyAddedKg
    FROM team LEFT JOIN entry ON entry."team" = team."name"
    AND entry."created" > current_timestamp - interval '7 day'
    GROUP BY team."name";