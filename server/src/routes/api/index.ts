import express from "express";
import chatlog from "./chatlog";

const router = express.Router();
router.use("/chatlog", chatlog);

export default router;
