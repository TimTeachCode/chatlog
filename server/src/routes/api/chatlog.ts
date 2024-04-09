import express from 'express';
import db from '../../utils/db';
import { sendAnonymizedReponse, sendUnanonymizedReponse } from '../../utils';

const router = express.Router();
router.use('/:id', async (req, res) => {
  const id = req.params.id;
  const { name } = req.query;

  const chatLog = await db.chatLog.findUnique({
    where: {
      id,
    },
    include: {
      messages: true,
    },
  });

  if (!chatLog) {
    res.status(404).json({ message: `Could not find chatlog with id ${id}` });
    return;
  }

  if (
    name &&
    chatLog.reportedName.toLowerCase() === name.toString().toLowerCase()
  ) {
    sendUnanonymizedReponse(chatLog, res);
  } else {
    sendAnonymizedReponse(chatLog, res);
  }
});

export default router;
