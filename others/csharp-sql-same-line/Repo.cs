class Repo { void First(object db) { db.FromSqlRaw("SELECT 1"); } void Second(object db) { db.FromSqlRaw("SELECT 2"); } }
