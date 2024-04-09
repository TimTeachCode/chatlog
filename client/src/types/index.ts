export type SystemMessage = {
  issuer: 'SYSTEM';
  message: string;
  time: Date;
};

export type UserMessage = {
  issuer: 'USER';
  name: string;
  uniqueId?: string;
  message: string;
  reporter: boolean;
  reported: boolean;
  time: Date;
};

export type Message = SystemMessage | UserMessage;

export type ChatLog = {
  id: string;
  anonymized: boolean;
  createdAt: Date;
  messages: Message[];
};
