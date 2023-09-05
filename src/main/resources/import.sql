-- Benutzer Imports

INSERT INTO public."USERENTITY" (
 "Email", "Username", "Passwort", "Role") VALUES (
'test@admin.test'::character varying, 'Karl'::character varying, '123456'::character varying, 'Admin'::character varying)
 returning id;

 INSERT INTO public."USERENTITY" (
 "Email", "Nachname", "Passwort", "Role", "Vorname") VALUES (
'test@member.test'::character varying, 'Rosa'::character varying, '123456'::character varying, 'Member'::character varying)
 returning id;

-- Buchungen Imports

INSERT INTO public."BUCHUNG" (
 "Datum", "Halbtag", "Status", user_id) VALUES (
'2023-09-05'::date, false::boolean, 'Approved'::character varying, true::boolean, '1'::bigint)
 returning id;

 INSERT INTO public."BUCHUNG" (
"Datum", "Halbtag", "Status", user_id) VALUES (
'2023-09-05'::date, false::boolean, 'Pending'::character varying, false::boolean, '2'::bigint)
 returning id;