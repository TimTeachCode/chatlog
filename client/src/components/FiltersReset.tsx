import { useFilters } from '@/store/use-filters';
import { Button } from './ui/button';
import { RotateCcw } from 'lucide-react';

export const FiltersReset = () => {
  const { reset } = useFilters();

  return (
    <Button onClick={reset}>
      <RotateCcw className='h-4 w-4 mr-2' />
      Filter zurücksetzen
    </Button>
  );
};
