import { ChatLog, Issuer, Message } from '@prisma/client';
import { Response } from 'express';

type ChatLogWithMessages = {
  messages: Message[];
} & ChatLog;

type NormalizedMessage = {
  issuer: string;
  uniqueId: string | undefined;
  name: string | undefined;
  message: string;
  reporter: boolean | undefined;
  reported: boolean | undefined;
  time: number;
};

export const sendAnonymizedReponse = (
  chatLog: ChatLogWithMessages,
  res: Response
) => {
  const { id, createdAt, messages } = chatLog;

  const result = {
    id,
    anonymized: true,
    createdAt,
    messages: anonymize(normalize(messages)),
  };

  res.status(200).json(result);
};

export const sendUnanonymizedReponse = (
  chatLog: ChatLogWithMessages,
  res: Response
) => {
  const { id, createdAt, messages } = chatLog;

  const result = {
    id,
    anonymized: false,
    createdAt,
    messages: normalize(messages),
  };

  res.status(200).json(result);
};

const anonymize = (messages: NormalizedMessage[]) => {
  let players: string[] = [];

  return messages.map((message) => ({
    ...message,
    uniqueId: undefined,
    name: message.name
      ? `Spieler ${
          players.includes(message.uniqueId!)
            ? players.indexOf(message.uniqueId!) + 1
            : players.push(message.uniqueId!)
        }`
      : undefined,
  }));
};

const normalize = (messages: Message[]): NormalizedMessage[] => {
  return messages.map((message) => ({
    issuer: message.issuer.toString(),
    uniqueId: message.issuer === Issuer.USER ? message.uuid! : undefined,
    name: message.issuer === Issuer.USER ? message.name! : undefined,
    message: message.message,
    reporter: message.issuer === Issuer.USER ? message.reporter : undefined,
    reported: message.issuer === Issuer.USER ? message.reported : undefined,
    time: message.time.getTime(),
  }));
};
