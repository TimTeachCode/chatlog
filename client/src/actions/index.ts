import { ChatLog } from '@/types';
import axios from 'axios';

export const loadChatLog = async (
  id: string,
  name?: string
): Promise<ChatLog | undefined> => {
  try {
    const fetchData = await axios.get(
      `${import.meta.env.VITE_API_URL}/${id}${name ? `?name=${name}` : ''}`
    );
    const { anonymized, createdAt, messages } = fetchData.data as ChatLog;

    return {
      id,
      anonymized,
      createdAt: new Date(createdAt),
      messages: messages.map((message) => ({
        ...message,
        time: new Date(message.time),
      })),
    };
  } catch (_error) {
    return undefined;
  }
};
