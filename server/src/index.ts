import express from "express";
import * as dotenv from "dotenv";
import cors from "cors";
import routes from "./routes";

dotenv.config();

const app = express();
app.use(cors());
app.use(routes);

const startServer = () => {
  try {
    const port = process.env.PORT || 8080;
    app.listen(port, () => console.log(`Server started on port ${port}`));
  } catch (error) {
    console.error(error);
  }
};

startServer();
