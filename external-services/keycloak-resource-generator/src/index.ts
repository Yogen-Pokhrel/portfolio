import seeder from "./process.js";

console.log("Seeding Started");
seeder.main().then((data) =>{console.log("Seeding completed");})
    .catch((error) =>{console.error("Seeding failed:" + error.message);})