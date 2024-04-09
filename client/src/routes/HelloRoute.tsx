import { hero } from '@/assets';

const HelloRoute = () => {
  return (
    <div className='min-h-[calc(100vh-178px)] h-full flex flex-col justify-center items-center gap-12 px-4'>
      <img src={hero} width={382} height={279} alt='hero' />
      <div className='flex flex-col justify-center items-center gap-2'>
        <h1 className='text-4xl font-bold'>Willkommen im ChatLog!</h1>
        <p className='text-lg font-medium text-neutral-600 dark:text-neutral-300'>
          Oooops! Ohne ID können wir dir leider nichts anzeigen...
        </p>
      </div>
    </div>
  );
};

export default HelloRoute;
